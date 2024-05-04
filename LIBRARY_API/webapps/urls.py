from django.contrib import admin
from django.urls import path, include
from django.conf import settings
from django.conf.urls.static import static
from rest_framework_simplejwt.views import TokenObtainPairView, TokenRefreshView
from information.views import RegisterView, CheckAccount


urlpatterns = [
    path("admin/", admin.site.urls),
    path("api/token/", TokenObtainPairView.as_view(), name="login"),
    path("api/token/refresh/", TokenRefreshView.as_view(), name="refresh"),
    path("api/register/", RegisterView.as_view(), name="register"),
    path("api/check-account/", CheckAccount.as_view(), name="check-account"),
    path("information/", include("information.urls")),
    path("management/", include("management.urls")),
]

urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
