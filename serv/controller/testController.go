package controller

import (
	"AFootGolf9/TheRastreator/entity"
	"time"

	"github.com/gin-gonic/gin"
)

func Test(c *gin.Context) {

	var location entity.LocationQuery
	c.BindJSON(&location)

	out := location.FinalTime == time.Time{}

	c.JSON(200, gin.H{
		"status": out,
	})
}
