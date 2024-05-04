from django.contrib import admin
from .models import User, Book, Reader, Librarian

admin.site.register(User)
admin.site.register(Book)
admin.site.register(Reader)
admin.site.register(Librarian)
