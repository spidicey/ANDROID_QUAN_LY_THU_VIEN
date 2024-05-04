from django.urls import path, include
from rest_framework.routers import DefaultRouter
from . import views


router = DefaultRouter()
router.register("sell-forms", views.SellFormViewSet, basename="sell-forms")
router.register("sell-form-details", views.SellFormDetailViewSet)
router.register("borrow-forms", views.BorrowFormViewSet, basename="borrow-forms")
router.register("borrow-form-details", views.BorrowFormDetailViewSet)
router.register("import-forms", views.ImportFormViewSet, basename="import-forms")
router.register("import-form-details", views.ImportFormDetailViewSet)
router.register("fine-forms", views.FineFormViewSet, basename="fine-forms")


urlpatterns = [
    path("", include(router.urls)),
    path("validate-forms/", views.ValidateFormView.as_view()),
    path("graph-book-sell/", views.GraphBookSellView.as_view()),
    path("graph-book-borrow/", views.GraphBookBorrowView.as_view()),
]
