# Minio Test App

This project demonstrates how to use the Minio SDK to interact with a Minio server that is behind an NGINX proxy, especially using presigned URLs. For reference check out [Configure NGINX Proxy for MinIO Server](https://min.io/docs/minio/linux/integrations/setup-nginx-proxy-with-minio.html).
Both options with dedicated domain and sub-domain are available in the repository, but you need to change the configuration manually to test.

## Requirements

- Java 11
- Maven
- Docker
- Minio server

## Installation and Setup

1. Clone this repository.
2. Ensure you have Docker installed and running.
3. Create a Minio server using Docker Compose.

```bash
docker-compose up --build
```

# Linux

As we need a DNS hostname that identifies the MinIO deployment, for local tests we simply add the application-defined name `minio.myapp.com` to the local name resolution file and do not use loopback.

- On linux: `/etc/hosts`

- On windows: `%windir%\System32\drivers\etc\hosts`


It should look something like this:
```
...
192.168.2.10    minio.myapp.com
192.168.2.10    console.myapp.com
```