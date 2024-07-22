package main

import (
	"github.com/gin-gonic/gin"

	"AFootGolf9/TheRastreator/controller"
	"AFootGolf9/TheRastreator/db"
	"AFootGolf9/TheRastreator/logger"
	"AFootGolf9/TheRastreator/repository"
)

func main() {
	database, err := db.Connect()
	if err != nil {
		panic(err)
	}
	logger.SetDB(database)
	repository.SetDB(database)

	r := gin.Default()
	r.POST("/authenticate", controller.Autenticate)
	r.POST("/newuser", controller.NewUser)

	r.Run(":8080")
}
