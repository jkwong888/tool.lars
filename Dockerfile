FROM websphere-liberty:19.0.0.3-kernel

USER default

RUN /opt/ibm/wlp/bin/installUtility install \
    cdi-1.0 \
    servlet-3.0 \
    mongodb-2.0 \
    jaxrs-1.1 \
    appSecurity-2.0 \
    ssl-1.0 \
    jndi-1.0

USER root
RUN wget https://public.dhe.ibm.com/ibmdl/export/pub/software/websphere/wasdev/downloads/wlp/LARS/larsServerPackage.jar -O /tmp/larsServerPackage.jar \
    && chmod 700 /tmp/larsServerPackage.jar \
    && java -jar /tmp/larsServerPackage.jar /opt/ibm/wlp \
    && mv /opt/ibm/wlp/usr/servers/larsServer/apps/*.war /opt/ibm/wlp/usr/servers/defaultServer/apps

COPY server/config/server.xml /opt/ibm/wlp/usr/servers/defaultServer/server.xml
RUN rm -rf /opt/ibm/wlp/usr/servers/larsServer

USER default
