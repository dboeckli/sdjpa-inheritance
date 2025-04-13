# Spring Data JPA Inheritance

This repository contains source code examples to support my course Hibernate and Spring Data JPA Beginner to Guru

## Additional Resources

This project focuses on inheritance in JPA. For more information about inheritance, transactions in database systems, and Spring Data JPA, please refer to the following documents in the `doc` folder:

- [Inheritance Overview](doc/InheritanceOverview.pdf): This document provides a comprehensive overview of inheritance in JPA.

### Deployment

To deploy all resources:
```bash
kubectl apply -f k8s/
```

To remove all resources:
```bash
kubectl delete -f k8s/
```

Check
```bash
kubectl get deployments -o wide
kubectl get pods -o wide
```