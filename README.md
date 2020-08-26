# Leetcode Dashboard


This is a simple application that pulls data from [leetcode](http://leetcode.com/) and prepares a Grafana dashboard.
The main purpose of this app is to try new technologies and frameworks.

The application uses

- Helidon ME
- Google Guice
- Prometheus and Grafana

## How to run it locally

### Building

I use maven to build applications. The following maven command builds the app and correspoding docker image (using jib plugin)

```bash
mvn clean package
```

### Run application

- Go to `etc/docker-compose`
- Change profiles that should be monitored in `prometheus.yml`
- Run `docker-compose up`
