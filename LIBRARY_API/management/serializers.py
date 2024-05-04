from rest_framework import serializers
from .models import *
from information.serializers import *


class SellFormSerializer(serializers.ModelSerializer):
    class Meta:
        model = SellForm
        exclude = ("details",)
        extra_kwargs = {
            "librarian": {
                "read_only": True,
            }
        }


class SellFormDetailSerializer(serializers.ModelSerializer):
    class Meta:
        model = SellFormDetail
        fields = "__all__"


class BorrowFormSerializer(serializers.ModelSerializer):
    class Meta:
        model = BorrowForm
        exclude = ("details",)
        extra_kwargs = {
            "librarian": {
                "read_only": True,
            }
        }


class BorrowFormDetailSerializer(serializers.ModelSerializer):
    class Meta:
        model = BorrowFormDetail
        fields = "__all__"


class ImportFormSerializer(serializers.ModelSerializer):
    class Meta:
        model = ImportForm
        exclude = ("details",)
        extra_kwargs = {
            "librarian": {
                "read_only": True,
            }
        }


class ImportFormDetailSerializer(serializers.ModelSerializer):
    class Meta:
        model = ImportFormDetail
        fields = "__all__"


class FineFormSerializer(serializers.ModelSerializer):
    class Meta:
        model = FineForm
        fields = "__all__"
        extra_kwargs = {
            "librarian": {
                "read_only": True,
            },
        }
