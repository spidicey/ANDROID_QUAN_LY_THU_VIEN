# Generated by Django 5.0.3 on 2024-04-23 02:05

import django.db.models.deletion
import management.models
from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('information', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='BorrowForm',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('created_date', models.DateField(auto_now_add=True)),
                ('expired_date', models.DateField(default=management.models.set_expired_date)),
                ('state', models.PositiveSmallIntegerField(default=0)),
                ('librarian', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='information.librarian')),
                ('reader', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='information.reader')),
            ],
            options={
                'ordering': ('state', '-created_date', '-id'),
            },
        ),
        migrations.CreateModel(
            name='BorrowFormDetail',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('returned_date', models.DateField(blank=True, null=True)),
                ('quantity', models.PositiveIntegerField()),
                ('returned_quantity', models.PositiveIntegerField(default=0)),
                ('state', models.PositiveSmallIntegerField(default=0)),
                ('book', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='information.book')),
                ('borrow_form', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='management.borrowform')),
            ],
        ),
        migrations.AddField(
            model_name='borrowform',
            name='details',
            field=models.ManyToManyField(blank=True, through='management.BorrowFormDetail', to='information.book'),
        ),
        migrations.CreateModel(
            name='FineForm',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('created_date', models.DateField(auto_now_add=True)),
                ('fee', models.FloatField(blank=True, default=0, null=True)),
                ('reason', models.CharField(blank=True, max_length=200, null=True)),
                ('is_deleted', models.BooleanField(default=False)),
                ('librarian', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='information.librarian')),
                ('reader', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='information.reader')),
            ],
            options={
                'ordering': ('is_deleted', '-created_date', '-id'),
            },
        ),
        migrations.CreateModel(
            name='ImportForm',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('created_date', models.DateField(auto_now_add=True)),
                ('supplier', models.CharField(max_length=200)),
                ('total', models.FloatField(default=0)),
                ('state', models.PositiveSmallIntegerField(default=0)),
                ('librarian', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='information.librarian')),
            ],
            options={
                'ordering': ('state', '-created_date', '-id'),
            },
        ),
        migrations.CreateModel(
            name='ImportFormDetail',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('quantity', models.PositiveIntegerField()),
                ('price', models.FloatField()),
                ('book', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='information.book')),
                ('import_form', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='management.importform')),
            ],
        ),
        migrations.AddField(
            model_name='importform',
            name='details',
            field=models.ManyToManyField(blank=True, through='management.ImportFormDetail', to='information.book'),
        ),
        migrations.CreateModel(
            name='SellForm',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('created_date', models.DateField(auto_now_add=True)),
                ('reason', models.CharField(blank=True, max_length=200, null=True)),
                ('total', models.FloatField(default=0)),
                ('state', models.PositiveSmallIntegerField(default=0)),
                ('librarian', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='information.librarian')),
            ],
            options={
                'ordering': ('state', '-created_date', '-id'),
            },
        ),
        migrations.CreateModel(
            name='SellFormDetail',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('quantity', models.PositiveIntegerField()),
                ('price', models.FloatField()),
                ('book', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='information.book')),
                ('sell_form', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='management.sellform')),
            ],
        ),
        migrations.AddField(
            model_name='sellform',
            name='details',
            field=models.ManyToManyField(blank=True, through='management.SellFormDetail', to='information.book'),
        ),
    ]