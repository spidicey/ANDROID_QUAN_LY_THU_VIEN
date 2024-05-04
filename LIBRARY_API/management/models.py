from django.db import models
from information.models import Book, Reader, Librarian
from datetime import date, timedelta


class SellForm(models.Model):
    created_date = models.DateField(auto_now_add=True)
    reason = models.CharField(max_length=200, null=True, blank=True)
    total = models.FloatField(default=0)
    state = models.PositiveSmallIntegerField(default=0)
    librarian = models.ForeignKey(Librarian, on_delete=models.PROTECT)
    details = models.ManyToManyField(Book, blank=True, through="SellFormDetail")

    class Meta:
        ordering = ("state", "-created_date", "-id")


class SellFormDetail(models.Model):
    sell_form = models.ForeignKey(SellForm, on_delete=models.CASCADE)
    book = models.ForeignKey(Book, on_delete=models.PROTECT)
    quantity = models.PositiveIntegerField()
    price = models.FloatField()


def set_expired_date():
    return date.today() + timedelta(days=15)


class BorrowForm(models.Model):
    created_date = models.DateField(auto_now_add=True)
    expired_date = models.DateField(default=set_expired_date)
    state = models.PositiveSmallIntegerField(default=0)
    reader = models.ForeignKey(Reader, on_delete=models.PROTECT)
    librarian = models.ForeignKey(Librarian, on_delete=models.PROTECT)
    details = models.ManyToManyField(Book, blank=True, through="BorrowFormDetail")

    class Meta:
        ordering = ("state", "-created_date", "-id")


class BorrowFormDetail(models.Model):
    borrow_form = models.ForeignKey(BorrowForm, on_delete=models.CASCADE)
    book = models.ForeignKey(Book, on_delete=models.PROTECT)
    returned_date = models.DateField(null=True, blank=True)
    quantity = models.PositiveIntegerField()
    returned_quantity = models.PositiveIntegerField(default=0)
    state = models.PositiveSmallIntegerField(default=0)


class ImportForm(models.Model):
    created_date = models.DateField(auto_now_add=True)
    supplier = models.CharField(max_length=200)
    total = models.FloatField(default=0)
    state = models.PositiveSmallIntegerField(default=0)
    librarian = models.ForeignKey(Librarian, on_delete=models.PROTECT)
    details = models.ManyToManyField(Book, blank=True, through="ImportFormDetail")

    class Meta:
        ordering = ("state", "-created_date", "-id")


class ImportFormDetail(models.Model):
    import_form = models.ForeignKey(ImportForm, on_delete=models.CASCADE)
    book = models.ForeignKey(Book, on_delete=models.PROTECT)
    quantity = models.PositiveIntegerField()
    price = models.FloatField()


class FineForm(models.Model):
    created_date = models.DateField(auto_now_add=True)
    fee = models.FloatField(default=0, null=True, blank=True)
    reason = models.CharField(max_length=200, null=True, blank=True)
    reader = models.ForeignKey(Reader, on_delete=models.PROTECT)
    librarian = models.ForeignKey(Librarian, on_delete=models.PROTECT)
    is_deleted = models.BooleanField(default=False)

    class Meta:
        ordering = ("is_deleted", "-created_date", "-id")
