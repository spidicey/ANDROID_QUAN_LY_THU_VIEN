from rest_framework_simplejwt.serializers import TokenObtainPairSerializer
from information.models import Librarian, Reader


class MyTokenObtainPairSerializer(TokenObtainPairSerializer):
    @classmethod
    def get_token(cls, user):
        token = super().get_token(user)

        # Add custom claims.
        token["email"] = user.email or ""
        token["is_staff"] = user.is_staff
        token["is_superuser"] = user.is_superuser
        match True:
            case user.is_superuser:
                token["name"] = "ADMINISTATOR"
                token["phone"] = ""
            case user.is_staff:
                profile = Librarian.objects.get(user=user)
                token["name"] = profile.full_name
                token["phone"] = profile.phone
                token["personal"] = profile.pk
            case _:
                profile = Reader.objects.get(user=user)
                token["name"] = profile.full_name
                token["phone"] = profile.phone
                token["personal"] = profile.pk

        return token
