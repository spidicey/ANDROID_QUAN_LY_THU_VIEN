from rest_framework import serializers
from .models import *


class RegisterSerializer(serializers.ModelSerializer):
    gender = serializers.BooleanField()
    phone = serializers.CharField(max_length=20)
    profile_image_path = serializers.CharField(max_length=255, required=False)

    class Meta:
        model = User
        fields = (
            "username",
            "password",
            "last_name",
            "first_name",
            "gender",
            "phone",
            "profile_image_path",
        )


class BookSerializer(serializers.ModelSerializer):
    class Meta:
        model = Book
        fields = "__all__"


class ReaderSerializer(serializers.ModelSerializer):
    class Meta:
        model = Reader
        fields = "__all__"


class LibrarianSerializer(serializers.ModelSerializer):
    class Meta:
        model = Librarian
        fields = "__all__"
