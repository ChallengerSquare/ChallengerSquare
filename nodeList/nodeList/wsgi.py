"""
WSGI config for nodeList project.

It exposes the WSGI callable as a module-level variable named ``application``.

For more information on this file, see
https://docs.djangoproject.com/en/5.0/howto/deployment/wsgi/
"""

import os

from django.core.wsgi import get_wsgi_application
from sockets.heartBeat import scheduler
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'nodeList.settings')

application = get_wsgi_application()
scheduler.start()