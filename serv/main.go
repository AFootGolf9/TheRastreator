package main

import (
	"fmt"

	"github.com/gin-gonic/gin"

	"AFootGolf9/TheRastreator/db"
	"AFootGolf9/TheRastreator/entity"
	"AFootGolf9/TheRastreator/logger"
	"AFootGolf9/TheRastreator/repository"
)

func GetLocationRegisters(c *gin.Context) {
	locations := repository.GetLocationRegisters()
	c.JSON(200, locations)
}

func InsertLocationRegister(c *gin.Context) {
	var location entity.LocationRegister
	c.BindJSON(&location)
	fmt.Print(location)
	repository.InsertLocationRegister(location.Latitude, location.Longitude)
	c.JSON(200, gin.H{
		"status": "Location registered successfully!",
	})
}

func main() {
	database, err := db.Connect()
	if err != nil {
		panic(err)
	}
	logger.SetDB(database)
	repository.SetDB(database)

	r := gin.Default()
	r.GET("/locations", GetLocationRegisters)
	r.POST("/location", InsertLocationRegister)

	r.Run(":8080")
}
