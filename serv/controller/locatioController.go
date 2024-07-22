package controller

import (
	"AFootGolf9/TheRastreator/entity"
	"AFootGolf9/TheRastreator/repository"

	"github.com/gin-gonic/gin"
)

func Register(c *gin.Context) {
	var location entity.LocationRegister
	c.BindJSON(&location)

	id := repository.GetClientByToken(location.Token)
	if id == 0 {
		c.JSON(401, gin.H{
			"error": "Invalid token",
		})
		return
	}
	repository.InsertLocationRegister(id, location.Latitude, location.Longitude)
	c.JSON(200, gin.H{
		"status": "Location registered successfully!",
	})
}
