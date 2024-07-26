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
	r.POST("/login", controller.Autenticate)
	r.POST("/create", controller.NewUser)
	r.POST("/register", controller.Register)

	r.Run(":8080")
}
