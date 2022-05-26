FROM payara/micro
COPY stw22.war $DEPLOY_DIR

ENV USERNAME=stw 
ENV PASSWORD=stw
ENV DB_DIR=/opt/payara/db
#RUN chmod 7777 db
ADD create-pool.txt /opt/payara/
CMD ["--deploymentDir","/opt/payara/deployments" , "--postbootcommandfile", "/opt/payara/create-pool.txt"]

