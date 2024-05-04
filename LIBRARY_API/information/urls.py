from django.urls import path, include
from rest_framework.routers import DefaultRouter
from . import views


router = DefaultRouter()
router.register("books", views.BookViewSet)
router.register("readers", views.ReaderViewSet)
router.register("librarians", views.LibrarianViewSet)


urlpatterns = [
    path("", include(router.urls)),
    path("graph-book-type/", views.GraphBookTypeView.as_view()),
]
