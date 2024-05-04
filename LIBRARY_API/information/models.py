from django.db import models
from django.contrib.auth.models import AbstractUser


class User(AbstractUser):
    email = models.EmailField(max_length=200, null=True)
    last_name = None
    first_name = None


class Book(models.Model):
    image = models.ImageField(upload_to="images/")
    title = models.CharField(max_length=100)
    author = models.CharField(max_length=100)
    category = models.CharField(max_length=100)
    bookshelf = models.PositiveIntegerField(null=True, blank=True)
    shelf = models.PositiveIntegerField(null=True, blank=True)
    inventory = models.PositiveIntegerField(default=0)
    quantity = models.PositiveIntegerField(default=0)

    def __str__(self) -> str:
        return self.title

    @staticmethod
    def update_book_inventory(id: int, quantity: int):
        if quantity != 0:
            book = Book.objects.get(pk=id)
            book.inventory += quantity
            book.quantity += quantity
            book.save()


class Profile(models.Model):
    last_name = models.CharField(max_length=50, null=True)
    first_name = models.CharField(max_length=50, null=True)
    gender = models.BooleanField(null=True)
    birthdate = models.DateField(null=True)
    address = models.CharField(max_length=200, null=True, blank=True)
    phone = models.CharField(max_length=20, null=True, blank=True)
    user = models.OneToOneField(
        to=User, null=True, blank=True, on_delete=models.PROTECT
    )

    class Meta:
        abstract = True

    def __str__(self) -> str:
        return f"{self.last_name} {self.first_name} - #{self.pk}"

    @property
    def full_name(self) -> str:
        return f"{self.last_name} {self.first_name}"


class Reader(Profile):
    profile_image_path = models.CharField(max_length=255, null=True, blank=True)


class Librarian(Profile):
    pass
