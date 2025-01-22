package controller

import (
	"AFootGolf9/TheRastreator/entity"
	"AFootGolf9/TheRastreator/repository"
	"time"

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

func GetLocationsWithParams(c *gin.Context) {
	var location entity.LocationQuery
	c.BindJSON(&location)

	id := repository.GetClientByToken(location.Token)
	print(id)
	if id == 0 {
		c.JSON(401, gin.H{
			"error": "Invalid token",
		})
		return
	}

	if location.InitialTime == "" {
		location.InitialTime = "0001-01-01 00:00:00"
	}
	if location.FinalTime == "" {
		location.FinalTime = "0001-01-01 00:00:00"
	}

	initialTime, err := time.Parse("2006-01-02 15:04:05", location.InitialTime)

	if err != nil {
		c.JSON(401, gin.H{
			"error": "Invalid initial time",
		})
		return
	}

	finalTime, err := time.Parse("2006-01-02 15:04:05", location.FinalTime)

	if err != nil {
		c.JSON(401, gin.H{
			"error": "Invalid final time",
		})
		return
	}

	if finalTime.IsZero() {
		finalTime = time.Now().Add(time.Hour * 1)
	}

	locations := repository.GetLocationsWithFullParams(initialTime, finalTime, id)

	c.JSON(200, gin.H{
		"locations": locations,
	})
}
