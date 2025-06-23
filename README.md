# Spring Data JPA Inheritance

This repository contains source code examples to support my course Hibernate and Spring Data JPA Beginner to Guru

## Additional Resources

This project focuses on inheritance in JPA. For more information about inheritance, transactions in database systems, and Spring Data JPA, please refer to the following documents in the `doc` folder:

- [Inheritance Overview](doc/InheritanceOverview.pdf): This document provides a comprehensive overview of inheritance in JPA.

## Deployment with Kubernetes

To run maven filtering for destination target/k8s
```bash
mvn clean install -DskipTests 
```

Deployment goes into the default namespace.

To deploy all resources:
```bash
kubectl apply -f target/k8s/
```

To remove all resources:
```bash
kubectl delete -f target/k8s/
```

Check
```bash
kubectl get deployments -o wide
kubectl get pods -o wide
```

You can use the actuator rest call to verify via port 30080

## Deployment with Helm

Be aware that we are using a different namespace here (not default).

To run maven filtering for destination target/helm
```bash
mvn clean install -DskipTests 
```

Go to the directory where the tgz file has been created after 'mvn install'
```powershell
cd target/helm/repo
```

unpack
```powershell
$file = Get-ChildItem -Filter *.tgz | Select-Object -First 1
tar -xvf $file.Name
```

install
```powershell
$APPLICATION_NAME = Get-ChildItem -Directory | Where-Object { $_.LastWriteTime -ge $file.LastWriteTime } | Select-Object -ExpandProperty Name
helm upgrade --install $APPLICATION_NAME ./$APPLICATION_NAME --namespace sdjpa-inheritance --create-namespace --wait --timeout 5m --debug --render-subchart-notes
```

show logs
```powershell
kubectl get pods -l app.kubernetes.io/name=$APPLICATION_NAME -n sdjpa-inheritance
```
replace $POD with pods from the command above
```powershell
kubectl logs $POD -n sdjpa-inheritance --all-containers
```

test
```powershell
helm test $APPLICATION_NAME --namespace sdjpa-inheritance --logs
```

uninstall
```powershell
helm uninstall $APPLICATION_NAME --namespace sdjpa-inheritance
```