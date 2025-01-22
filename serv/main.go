package main

import (
	"github.com/gin-contrib/cors"
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

	config := cors.DefaultConfig()
	config.AllowAllOrigins = true
	config.AllowHeaders = []string{"Authorization", "Origin", "Content-Length", "Content-Type"}
	r.Use(cors.New(config))

	r.GET("/test", controller.Test)
	r.GET("/login", controller.ValidateToken)
	r.POST("/login", controller.Autenticate)
	r.POST("/create", controller.NewUser)
	r.POST("/register", controller.Register)
	r.GET("/locations", controller.GetLocationsWithParams)

	// TODO: get a URL for letsencrypt
	// log.Fatal(autotls.Run(r, ""))

	r.Run(":8080")
}
