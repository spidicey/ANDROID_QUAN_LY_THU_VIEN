from datetime import date

from django.db.transaction import atomic, rollback
from rest_framework import viewsets, status
from rest_framework.request import Request
from rest_framework.response import Response
from rest_framework.decorators import action
from rest_framework.views import APIView
from .serializers import *


class SellFormViewSet(viewsets.ModelViewSet):
    serializer_class = SellFormSerializer

    def get_queryset(self):
        librarian = Librarian.objects.get(user=self.request.user)
        return SellForm.objects.filter(librarian=librarian)

    @atomic
    def create(self, request: Request, *args, **kwargs):
        try:
            # CREATE FORM.
            serializer = SellFormSerializer(data=request.data["form"])
            if serializer.is_valid(raise_exception=True):
                form: SellForm = serializer.save(
                    librarian=Librarian.objects.get(user=request.user)
                )
                # CREATE DETAILS.
                total = 0
                for detail in request.data["details"]:
                    total += detail["quantity"] * detail["price"]
                    # CREATE DETAIL.
                    detail["sell_form"] = form.pk
                    serializer = SellFormDetailSerializer(data=detail)
                    if serializer.is_valid(raise_exception=True):
                        serializer.save()
                    # UPDATE BOOK INVENTORY.
                    Book.update_book_inventory(detail["book"], -detail["quantity"])
                form.total = total
                form.save()
            # SUCCESS RESPONE.
            return Response(status=status.HTTP_200_OK)
        except:
            rollback()
            # FAILURE RESPONE.
            return Response(status=status.HTTP_400_BAD_REQUEST)

    @atomic
    def update(self, request: Request, *args, **kwargs):
        try:
            # UPDATE FORM.
            form_data = request.data["form"]
            form = SellForm.objects.get(pk=form_data["id"])
            form.reason = form_data["reason"]
            form.state = 0
            form.save()
            # UPDATE DETAILS.
            details_data = request.data["details"]
            deleted_details: list[SellFormDetail] = []  # USE TO STORE DELETED DETAILS.
            total = 0
            for old_detail in SellFormDetail.objects.filter(sell_form=form):
                for new_detail in details_data:
                    if old_detail.book.pk == new_detail["book"]:
                        total += new_detail["quantity"] * new_detail["price"]
                        # UPDATE BOOK INVENTORY.
                        variance = old_detail.quantity - new_detail["quantity"]
                        Book.update_book_inventory(new_detail["book"], variance)
                        # UPDATE DETAIL.
                        old_detail.quantity = new_detail["quantity"]
                        old_detail.price = new_detail["price"]
                        old_detail.save()
                        # REMOVE UPDATED DETAIL.
                        details_data.remove(new_detail)
                        break
                else:
                    deleted_details.append(old_detail)
            # DELETE DETAILS.
            for detail in deleted_details:
                # UPDATE BOOK INVENTORY.
                Book.update_book_inventory(detail.book.pk, detail.quantity)
                # DELETE DETAIL.
                detail.delete()
            # CREATE DETAILS.
            for detail in details_data:
                total += detail["quantity"] * detail["price"]
                # UPDATE BOOK INVENTORY.
                Book.update_book_inventory(detail["book"], -detail["quantity"])
                # CREATE DETAIL.
                detail.pop("id")
                detail["sell_form"] = form
                detail["book"] = Book.objects.get(pk=detail["book"])
                SellFormDetail.objects.create(**detail)
            # UPDATE TOTAL.
            form.total = total
            form.save()
            # SUCCESS RESPONE.
            return Response(status=status.HTTP_200_OK)
        except:
            rollback()
            # FAILURE RESPONE.
            return Response(status=status.HTTP_400_BAD_REQUEST)

    @atomic
    def destroy(self, request: Request, pk: int):
        try:
            # UPDATE FORM.
            form = SellForm.objects.get(pk=pk)
            form.state = 3
            form.save()
            # UPDATE BOOK INVENTORY.
            for detail in SellFormDetail.objects.filter(sell_form=form):
                Book.update_book_inventory(detail.book.pk, detail.quantity)
            # SUCCESS RESPONE.
            return Response(status=status.HTTP_200_OK)
        except:
            rollback()
            # FAILURE RESPONE.
            return Response(status=status.HTTP_400_BAD_REQUEST)

    @action(methods=["patch"], detail=True)
    def complete_form(self, request: Request, pk: int):
        try:
            form = SellForm.objects.get(pk=pk)
            form.state = 2
            form.save()
            # SUCCESS RESPONE.
            return Response(status=status.HTTP_200_OK)
        except:
            # FAILURE RESPONE.
            return Response(status=status.HTTP_400_BAD_REQUEST)

    @action(methods=["patch"], detail=True)
    def validate_form(self, request: Request, pk: int):
        try:
            form = SellForm.objects.get(pk=pk)
            form.state = 1
            form.save()
            # SUCCESS RESPONE.
            return Response(status=status.HTTP_200_OK)
        except:
            # FAILURE RESPONE.
            return Response(status=status.HTTP_400_BAD_REQUEST)


class SellFormDetailViewSet(viewsets.ModelViewSet):
    queryset = SellFormDetail.objects.all()
    serializer_class = SellFormDetailSerializer

    @action(methods=["get"], detail=False)
    def filter_by_sell_form_id(self, request: Request):
        # FILTER DATA.
        form_id = request.query_params.get("id", -1)
        result = SellFormDetail.objects.filter(sell_form__pk=form_id)
        # PYTHON -> JSON.
        data = SellFormDetailSerializer(result, many=True).data
        # SUCCESS RESPONE.
        return Response(data=data, status=status.HTTP_200_OK)

    @action(methods=["get"], detail=False)
    def list_book_of_sell_form(self, request: Request):
        # FILTER DATA.
        is_validate = request.query_params.get("is_validate") == "true"
        form_id = request.query_params.get("id", -1)
        form = SellForm.objects.get(pk=form_id)
        details = SellFormDetail.objects.filter(sell_form=form)
        result = [detail.book for detail in details]
        # ADD AVAILABLE BOOKS IF FORM CAN BE MODIFIED.
        if form.state <= 1 and not is_validate:
            available_books = Book.objects.filter(quantity__gt=0)
            result += [book for book in available_books if book not in result]
        # PYTHON -> JSON.
        data = BookSerializer(result, context={"request": request}, many=True).data
        # SUCCESS RESPONE.
        return Response(data=data, status=status.HTTP_200_OK)


class BorrowFormViewSet(viewsets.ModelViewSet):
    serializer_class = BorrowFormSerializer

    def get_queryset(self):
        librarian = Librarian.objects.get(user=self.request.user)
        return BorrowForm.objects.filter(librarian=librarian)

    @atomic
    def create(self, request: Request, *args, **kwargs):
        try:
            # CREATE FORM.
            serializer = BorrowFormSerializer(data=request.data["form"])
            if serializer.is_valid(raise_exception=True):
                form: BorrowForm = serializer.save(
                    librarian=Librarian.objects.get(user=request.user)
                )
                # CREATE DETAILS.
                for detail in request.data["details"]:
                    # CREATE DETAIL.
                    detail["borrow_form"] = form.pk
                    serializer = BorrowFormDetailSerializer(data=detail)
                    if serializer.is_valid(raise_exception=True):
                        serializer.save()
                    # UPDATE BOOK INVENTORY.
                    Book.update_book_inventory(detail["book"], -detail["quantity"])
            # SUCCESS RESPONSE.
            return Response(status=status.HTTP_200_OK)
        except:
            rollback()
            # FAILURE RESPONSE.
            return Response(status=status.HTTP_400_BAD_REQUEST)

    @atomic
    def update(self, request: Request, *args, **kwargs):
        try:
            form_id = kwargs["pk"]
            form = BorrowForm.objects.get(pk=form_id)
            # UPDATE DETAILS.
            is_completed = True
            details_data = request.data
            for old_detail in BorrowFormDetail.objects.filter(borrow_form=form):
                for new_detail in details_data:
                    if old_detail.pk == new_detail["id"]:
                        old_quantity = old_detail.returned_quantity
                        new_quantity = new_detail["returned_quantity"]
                        new_state = new_detail["state"]
                        # UPDATE BOOK INVENTORY.
                        variance = new_quantity - old_quantity
                        Book.update_book_inventory(new_detail["book"], variance)
                        # UPDATE DETAIL.
                        old_detail.returned_quantity = new_quantity
                        old_detail.state = new_state
                        if new_state > 0:
                            old_detail.returned_date = date.today()
                        else:
                            is_completed = False
                        old_detail.save()
                        break
            # UPDATE FORM.
            if is_completed:
                form.state = 1
                form.save()
            # SUCCESS RESPONE.
            return Response(status=status.HTTP_200_OK)
        except:
            rollback()
            # FAILURE RESPONE.
            return Response(status=status.HTTP_400_BAD_REQUEST)


class BorrowFormDetailViewSet(viewsets.ModelViewSet):
    queryset = BorrowFormDetail.objects.all()
    serializer_class = BorrowFormDetailSerializer

    @action(methods=["get"], detail=False)
    def filter_by_borrow_form_id(self, request: Request):
        # FILTER DATA.
        form_id = request.query_params.get("id", -1)
        result = BorrowFormDetail.objects.filter(borrow_form__pk=form_id)
        # PYTHON -> JSON.
        data = BorrowFormDetailSerializer(result, many=True).data
        # SUCCESS RESPONE.
        return Response(data=data, status=status.HTTP_200_OK)

    @action(methods=["get"], detail=False)
    def list_book_of_borrow_form(self, request: Request):
        # FILTER DATA.
        form_id = request.query_params.get("id", -1)
        details = BorrowFormDetail.objects.filter(borrow_form__pk=form_id)
        result = [detail.book for detail in details]
        # PYTHON -> JSON.
        data = BookSerializer(result, context={"request": request}, many=True).data
        # SUCCESS RESPONE.
        return Response(data=data, status=status.HTTP_200_OK)


class ImportFormViewSet(viewsets.ModelViewSet):
    serializer_class = ImportFormSerializer

    def get_queryset(self):
        librarian = Librarian.objects.get(user=self.request.user)
        return ImportForm.objects.filter(librarian=librarian)

    @atomic
    def create(self, request: Request, *args, **kwargs):
        try:
            # CREATE FORM.
            serializer = ImportFormSerializer(data=request.data["form"])
            if serializer.is_valid(raise_exception=True):
                form: ImportForm = serializer.save(
                    librarian=Librarian.objects.get(user=request.user)
                )
                # CREATE DETAILS.
                total = 0
                for detail in request.data["details"]:
                    total += detail["quantity"] * detail["price"]
                    # CREATE DETAIL.
                    detail["import_form"] = form.pk
                    serializer = ImportFormDetailSerializer(data=detail)
                    if serializer.is_valid(raise_exception=True):
                        serializer.save()
                form.total = total
                form.save()
            # SUCCESS RESPONE.
            return Response(status=status.HTTP_200_OK)
        except:
            rollback()
            # FAILURE RESPONE.
            return Response(status=status.HTTP_400_BAD_REQUEST)

    @atomic
    def update(self, request: Request, *args, **kwargs):
        try:
            # UPDATE FORM.
            form_data = request.data["form"]
            form = ImportForm.objects.get(pk=form_data["id"])
            form.supplier = form_data["supplier"]
            form.state = 0
            form.save()
            # UPDATE DETAILS.
            details_data = request.data["details"]
            deleted_details: list[ImportFormDetail] = []
            total = 0
            # USE TO STORE DELETED DETAILS.
            for old_detail in ImportFormDetail.objects.filter(import_form=form):
                for new_detail in details_data:
                    if old_detail.book.pk == new_detail["book"]:
                        # ACCUMULATE TOTAL.
                        total += new_detail["quantity"] * new_detail["price"]
                        # UPDATE DETAIL.
                        old_detail.quantity = new_detail["quantity"]
                        old_detail.price = new_detail["price"]
                        old_detail.save()
                        # REMOVE UPDATED DETAIL.
                        details_data.remove(new_detail)
                        break
                else:
                    deleted_details.append(old_detail)
            # DELETE DETAILS.
            for detail in deleted_details:
                detail.delete()
            # CREATE DETAILS.
            for detail in details_data:
                # ACCUMULATE QUANTITY.
                total += detail["quantity"] * detail["price"]
                # CREATE DETAIL.
                detail.pop("id")
                detail["import_form"] = form
                detail["book"] = Book.objects.get(pk=detail["book"])
                ImportFormDetail.objects.create(**detail)
            # UPDATE TOTAL.
            form.total = total
            form.save()
            # SUCCESS RESPONE.
            return Response(status=status.HTTP_200_OK)
        except:
            rollback()
            # FAILURE RESPONE.
            return Response(status=status.HTTP_400_BAD_REQUEST)

    @atomic
    def destroy(self, request: Request, pk: int):
        try:
            # UPDATE FORM.
            form = ImportForm.objects.get(pk=pk)
            form.state = 3
            form.save()
            # SUCCESS RESPONE.
            return Response(status=status.HTTP_200_OK)
        except:
            # FAILURE RESPONE.
            return Response(status=status.HTTP_400_BAD_REQUEST)

    @action(methods=["patch"], detail=True)
    def complete_form(self, request: Request, pk: int):
        try:
            form = ImportForm.objects.get(pk=pk)
            form.state = 2
            form.save()
            # UPDATE BOOK INVENTORY.
            for detail in ImportFormDetail.objects.filter(import_form=form):
                Book.update_book_inventory(detail.book.pk, detail.quantity)
            # SUCCESS RESPONE.
            return Response(status=status.HTTP_200_OK)
        except:
            rollback()
            # FAILURE RESPONE.
            return Response(status=status.HTTP_400_BAD_REQUEST)

    @action(methods=["patch"], detail=True)
    def validate_form(self, request: Request, pk: int):
        try:
            form = ImportForm.objects.get(pk=pk)
            form.state = 1
            form.save()
            # SUCCESS RESPONE.
            return Response(status=status.HTTP_200_OK)
        except:
            # FAILURE RESPONE.
            return Response(status=status.HTTP_400_BAD_REQUEST)


class ImportFormDetailViewSet(viewsets.ModelViewSet):
    queryset = ImportFormDetail.objects.all()
    serializer_class = ImportFormDetailSerializer

    @action(methods=["get"], detail=False)
    def filter_by_import_form_id(self, request: Request):
        # FILTER DATA.
        form_id = request.query_params.get("id", -1)
        result = ImportFormDetail.objects.filter(import_form__pk=form_id)
        # PYTHON -> JSON.
        data = ImportFormDetailSerializer(result, many=True).data
        # SUCCESS RESPONE.
        return Response(data=data, status=status.HTTP_200_OK)

    @action(methods=["get"], detail=False)
    def list_book_of_import_form(self, request: Request):
        # FILTER DATA.
        is_validate = request.query_params.get("is_validate") == "true"
        form_id = request.query_params.get("id", -1)
        form = ImportForm.objects.get(pk=form_id)
        details = ImportFormDetail.objects.filter(import_form=form)
        result = [detail.book for detail in details]
        # ADD AVAILABLE BOOKS IF FORM CAN BE MODIFIED.
        if form.state <= 1 and not is_validate:
            available_books = Book.objects.all()
            result += [book for book in available_books if book not in result]
        # PYTHON -> JSON.
        data = BookSerializer(result, context={"request": request}, many=True).data
        # SUCCESS RESPONE.
        return Response(data=data, status=status.HTTP_200_OK)


class FineFormViewSet(viewsets.ModelViewSet):
    serializer_class = FineFormSerializer

    def get_queryset(self):
        librarian = Librarian.objects.get(user=self.request.user)
        return FineForm.objects.filter(librarian=librarian)

    def create(self, request: Request, *args, **kwargs):
        try:
            serializer = FineFormSerializer(data=request.data)
            if serializer.is_valid():
                serializer.save(librarian=Librarian.objects.get(user=request.user))
                return Response(status=status.HTTP_200_OK)
        except:
            return Response(status=status.HTTP_400_BAD_REQUEST)

    def destroy(self, request: Request, pk: int):
        try:
            # UPDATE FORM.
            form = FineForm.objects.get(pk=pk)
            form.is_deleted = True
            form.save()
            # SUCCESS RESPONE.
            return Response(status=status.HTTP_200_OK)
        except:
            # FAILURE RESPONE.
            return Response(status=status.HTTP_400_BAD_REQUEST)


class ValidateFormView(APIView):

    def get(self, request: Request):
        # FILTER DATA.
        sell_forms = SellForm.objects.filter(state=0)
        import_forms = ImportForm.objects.filter(state=0)
        # PYTHON -> JSON.
        data = {
            "sell_forms": SellFormSerializer(sell_forms, many=True).data,
            "import_forms": ImportFormSerializer(import_forms, many=True).data,
        }
        # SUCCESS RESPONE.
        return Response(data=data, status=status.HTTP_200_OK)


class GraphBookSellView(APIView):
    def get(self, request: Request):
        year = date.today().year
        # FILTER DATA.
        result = {}
        for form in SellForm.objects.filter(created_date__year=year):
            month = form.created_date.month
            total = sum(
                [
                    detail.quantity * detail.price
                    for detail in SellFormDetail.objects.filter(sell_form=form)
                ]
            )
            if month not in result:
                result[month] = total
            else:
                result[month] += total

        data = [{"month": key, "total": value} for key, value in result.items()]
        # SUCCESS RESPONE.
        return Response(data=data, status=status.HTTP_200_OK)


class GraphBookBorrowView(APIView):
    def get(self, request: Request):
        # FILTER DATA.
        result = {}
        for detail in BorrowFormDetail.objects.all():
            book = detail.book
            if book.pk not in result:
                result[book.pk] = [book.title, detail.quantity]
            else:
                result[book.pk][1] += detail.quantity

        data = [
            {"rank": index, "title": value[0], "count": value[1]}
            for index, value in enumerate(result.values(), start=1)
        ]
        data = sorted(data, key=lambda e: e["rank"])[:10]
        # SUCCESS RESPONE.
        return Response(data=data, status=status.HTTP_200_OK)
