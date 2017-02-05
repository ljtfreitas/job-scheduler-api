# job-scheduler-api
Simple Spring Scheduler (cron based) API sample

The execution of the job will display the message in the System.out.

## Running
``mvn spring-boot:run``

Start server on 8080 port

## Create new job
Sample: every ten seconds.
```bash
curl -H "Content-Type: application/json" -X POST http://localhost:8080/api/jobs -d '{"name": "job-name", "msg": "Hello World", "cron": "*/10 * * * * *"}'
```

## List all jobs
```
curl http://localhost:8080/api/jobs
```

## Delete job
```
curl -X DELETE http://localhost:8080/api/jobs/job-name
```
