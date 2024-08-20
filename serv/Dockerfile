FROM golang:1.22

WORKDIR /app

COPY go.mod ./
COPY go.sum ./

RUN go mod download

COPY *.go ./
COPY entity/* ./entity/
COPY db/* ./db/
COPY logger/* ./logger/
COPY controller/* ./controller/
COPY repository/* ./repository/
COPY util/* ./util/

RUN CGO_ENABLED=0 GOOS=linux go build -o /simple-go-app

EXPOSE 80
EXPOSE 433

CMD ["/simple-go-app"]
