
== What you'll build

You'll build a microservice application that uses Netflix Ribbon and Spring Cloud Netflix to provide client-side load balancing in calls to another microservice.

[[initial]]
== Write a server service

Our &#8220;server&#8221; service is called Say Hello-Service. It will return a random greeting (picked out of a static list of three) from an endpoint accessible at `/greeting`. We're going to run multiple instances of this application locally alongside a client service application.

== Access from a client service

The Hello-Client application will be what our user sees. It will make a call to the Hello-Service application to get a greeting and then send that to our user when the user visits the endpoint at `/hello`.

== Load balance across server instances

Now we can access `/hello` on the Hello-Service and see a friendly greeting:

----
$ curl http://localhost:8888/hello
Greetings, Thiru!

$ curl http://localhost:8888/hi?name=Chinna
Salutations, Chinna!
----

== Trying it out

Run the Say Hello-Service using port 8090:

----
$ mvn spring-boot:run -Dserver.port=8090
----

Run other instances on port 8091:

----
$ mvn spring-boot:run -Dserver.port=8091
----

Then start up the Hello-Client service. Access `localhost:8888/hello` and then watch the Hello-Service instances. You can see Ribbon's pings arriving every 15 seconds:

----
2016-03-09 21:13:22.115  INFO 90046 --- [nio-8090-exec-1] hello.SayHelloApplication                : Access /
2016-03-09 21:13:22.629  INFO 90046 --- [nio-8090-exec-3] hello.SayHelloApplication                : Access /
----

And your requests to the Hello-Service should result in calls to Say Hello being spread across the running instances in round-robin form:

----
2016-03-09 21:15:28.915  INFO 90046 --- [nio-8090-exec-7] hello.SayHelloApplication                : Access /greeting
----

Now shut down a Hello-Service server instance. Once Ribbon has pinged the down instance and considers it down, you should see requests begin to be balanced across the remaining instances.

== Summary

Congratulations! You've just developed a Spring application that performs client-side load balancing for calls to another application.


Source:
https://github.com/spring-guides/gs-client-side-load-balancing
