import base64
from django.core.files.base import ContentFile
from django.db.transaction import atomic, rollback
from rest_framework import viewsets
from rest_framework.views import APIView
from rest_framework.request import Request
from rest_framework.response import Response
from rest_framework import status
from .models import User, Book, Reader, Librarian
from .serializers import (
    RegisterSerializer,
    BookSerializer,
    ReaderSerializer,
    LibrarianSerializer,
)


class CheckAccount(APIView):
    def post(self, request: Request):
        if User.objects.filter(username=request.data["username"]):
            return Response(status=status.HTTP_400_BAD_REQUEST)
        else:
            return Response(status=status.HTTP_200_OK)


class RegisterView(APIView):
    @atomic
    def post(self, request: Request):
        data = request.data
        try:
            serializer = RegisterSerializer(data=data)
            if serializer.is_valid(raise_exception=True):
                username, password = data.pop("username"), data.pop("password")
                data["user"] = User.objects.create_user(
                    username=username, password=password
                )
                Reader.objects.create(**data)
                return Response(status=status.HTTP_200_OK)
        except:
            rollback()
            return Response(status=status.HTTP_400_BAD_REQUEST)


class BookViewSet(viewsets.ModelViewSet):
    queryset = Book.objects.all()
    serializer_class = BookSerializer

    def create(self, request: Request, *args, **kwargs):
        data = request.data
        try:
            # CONVERT BYTE ARRAY TO IMAGE FILE.
            data["image"] = BookViewSet.convert_to_file(data["image"], data["title"])
            # JSON -> OBJECT.
            serializer = BookSerializer(data=data)
            if serializer.is_valid(raise_exception=True):
                serializer.save()
                return Response(status=status.HTTP_200_OK)
        except:
            return Response(status=status.HTTP_400_BAD_REQUEST)

    def update(self, request, *args, **kwargs):
        data = request.data
        try:
            bytes_str = data.pop("image")
            if bytes_str.find("media") == -1:
                # CONVERT BYTE ARRAY TO IMAGE FILE.
                data["image"] = BookViewSet.convert_to_file(bytes_str, data["title"])
                # UPDATE WHEN IMAGE CHANGED.
                serializer = BookSerializer(data=data)
                if serializer.is_valid(raise_exception=True):
                    instance = Book.objects.get(pk=data["id"])
                    serializer.update(instance, serializer.validated_data)
            else:
                Book.objects.filter(pk=data["id"]).update(**data)
            return Response(status=status.HTTP_200_OK)
        except:
            return Response(status=status.HTTP_400_BAD_REQUEST)

    @staticmethod
    def convert_to_file(bytes_str: str, file_name: str) -> ContentFile:
        return ContentFile(base64.b64decode(bytes_str), f"{file_name}.jpg")


class ReaderViewSet(viewsets.ModelViewSet):
    queryset = Reader.objects.all()
    serializer_class = ReaderSerializer

    def list(self, request: Request, *args, **kwargs):
        data = [
            {
                **dict(ReaderSerializer(reader).data),
                "state": reader.user and reader.user.is_active,
            }
            for reader in Reader.objects.all()
        ]

        return Response(data=data, status=status.HTTP_200_OK)

    @atomic
    def update(self, request: Request, *args, **kwargs):
        data = request.data
        user_state = data.pop("state")

        try:
            serializer = ReaderSerializer(data=data)
            if serializer.is_valid(raise_exception=True):
                # UPDATE STATE.
                instance = Reader.objects.get(pk=data["id"])
                instance.user.is_active = user_state
                instance.user.save()
                # UPDATE INFORMATION.
                serializer.update(instance, serializer.validated_data)
                return Response(status=status.HTTP_200_OK)
        except:
            rollback()
            return Response(status=status.HTTP_400_BAD_REQUEST)


class LibrarianViewSet(viewsets.ModelViewSet):
    queryset = Librarian.objects.all()
    serializer_class = LibrarianSerializer

    def list(self, request: Request, *args, **kwargs):
        data = [
            {
                **dict(LibrarianSerializer(librarian).data),
                "state": librarian.user and librarian.user.is_active,
            }
            for librarian in Librarian.objects.all()
        ]

        return Response(data=data, status=status.HTTP_200_OK)

    @atomic
    def create(self, request: Request, *args, **kwargs):
        data = request.data
        data.pop("state")

        try:
            serializer = LibrarianSerializer(data=data)
            if serializer.is_valid(raise_exception=True):
                librarian: Librarian = serializer.save()
                librarian.user = User.objects.create_user(
                    username=f"{librarian.last_name}_{librarian.first_name}_{librarian.pk}".lower(),
                    password=f"{librarian.last_name}_{librarian.first_name}".lower(),
                )
                librarian.save()

                return Response(status=status.HTTP_200_OK)
        except:
            rollback()
            return Response(status=status.HTTP_400_BAD_REQUEST)

    @atomic
    def update(self, request: Request, *args, **kwargs):
        data = request.data
        user_state = data.pop("state")

        try:
            serializer = LibrarianSerializer(data=data)
            if serializer.is_valid(raise_exception=True):
                # UPDATE STATE.
                instance = Librarian.objects.get(pk=data["id"])
                instance.user.is_active = user_state
                instance.user.save()
                # UPDATE INFORMATION.
                serializer.update(instance, serializer.validated_data)
                return Response(status=status.HTTP_200_OK)
        except:
            rollback()
            return Response(status=status.HTTP_400_BAD_REQUEST)


class GraphBookTypeView(APIView):
    def get(self, request: Request):
        # FILTER DATA.
        result = {}
        for book in Book.objects.all():
            category = book.category
            if category not in result:
                result[category] = 1
            else:
                result[category] += 1

        data = [{"category": key, "count": value} for key, value in result.items()]
        # SUCCESS RESPONE.
        return Response(data=data, status=status.HTTP_200_OK)
