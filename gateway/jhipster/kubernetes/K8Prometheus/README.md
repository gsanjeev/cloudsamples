# JHipster generated kubernetes configuration

## Preparation

You will need to push your image to a registry. If you have not done so, use the following commands to tag and push the images:

```
$ docker image tag blog gsanjeev/blog
$ docker push gsanjeev/blog
$ docker image tag store gsanjeev/store
$ docker push gsanjeev/store
```

## Deployment

You can deploy your apps by running:

```
$ kubectl apply -f prometheus-tpr.yml
$ kubectl apply -f registry

$ kubectl apply -f blog
$ kubectl apply -f store
```

## Exploring your services


Use these commands to find your application's IP addresses:

```
$ kubectl get svc blog
```

## Scaling your deployments

You can scale your apps using 

```
$ kubectl scale deployment <app-name> --replicas <replica-count>
```

## zero-downtime deployments

The default way to update a running app in kubernetes, is to deploy a new image tag to your docker registry and then deploy it using

```
$ kubectl set image deployment/<app-name>-app <app-name>=<new-image> 
```

Using livenessProbes and readinessProbe allows you to tell kubernetes about the state of your apps, in order to ensure availablity of your services. You will need minimum 2 replicas for every app deployment, you want to have zero-downtime deployed. This is because the rolling upgrade strategy first kills a running replica in order to place a new. Running only one replica, will cause a short downtime during upgrades.

## Monitoring tools


### Prometheus metrics

If not already done, install the [Prometheus operator by CoreOS](https://github.com/coreos/prometheus-operator). You can quickly deploy the operator using 

**hint**: use must build your apps with `prometheus` profile active!

```
$ kubectl create -f https://raw.githubusercontent.com/coreos/prometheus-operator/master/bundle.yaml
```

The prometheus instance for your apps can be explored using

```
$ kubectl get svc prometheus-default
```

## JHipster registry

The registry is deployed using a headless service in kubernetes, so the primary service has no IP address, and cannot get a node port. You can create a secondary service for any type, using:

```
$ kubectl expose service jhipster-registry --type=NodePort --name=exposed-registry
```

and explore the details using

```
$ kubectl get svc exposed-registry
```

For scaling the JHipster registry, use

```
$ kubectl scale statefulset jhipster-registry --replicas 3
```


## Troubleshooting

> my apps doesn't get pulled, because of 'imagePullBackof'

check the registry your kubernetes cluster is accessing. If you are using a private registry, you should add it to your namespace by `kubectl create secret docker-registry` (check the [docs](https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/) for more info)

> my apps get killed, before they can boot up

This can occur, if your cluster has low resource (e.g. Minikube). Increase the `initialDelySeconds` value of livenessProbe of your deployments

> my apps are starting very slow, despite I have a cluster with many resources

The default setting are optimized for middle scale clusters. You are free to increase the JAVA_OPTS environment variable, and resource requests and limits to improve the performance. Be careful!

> I have selected prometheus but no targets are visible

This depends on the setup of prometheus operator and the access control policies in your cluster. Version 1.6.0+ is needed for the RBAC setup to work.

> I have selected prometheus, but my targets never get scraped

This means your apps are probably not built using the `prometheus` profile in Maven/Gradle

> my SQL based microservice stuck during liquibase initialization when running multiple replicas

Somethimes the database changelog lock gets corrupted. You will need to connect to the database using `kubectl exec -it` and remove all lines of liquibases `databasechangeloglock` table.
