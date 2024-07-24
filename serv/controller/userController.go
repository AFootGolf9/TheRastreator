package controller

import (
	"AFootGolf9/TheRastreator/entity"
	"AFootGolf9/TheRastreator/repository"
	"AFootGolf9/TheRastreator/util"
	"fmt"

	"github.com/gin-gonic/gin"
)

func validateUser(user string, pass string) bool {
	userDB := repository.GetUserByName(user)
	if userDB == nil {
		return false
	}
	return util.ComparePass(userDB.Pass, pass)
}

func Autenticate(c *gin.Context) {
	var user entity.UserJson
	c.BindJSON(&user)
	if validateUser(user.User, user.Pass) {
		user = *repository.GetUserByName(user.User)
		token := repository.NewToken(user.Id)
		c.JSON(200, gin.H{
			"token": token,
		})
	} else {
		c.JSON(401, gin.H{
			"error": "Invalid credentials",
		})
	}
}

func NewUser(c *gin.Context) {
	var user entity.UserJson
	c.BindJSON(&user)
	if repository.GetUserByName(user.User) != nil {
		fmt.Print("passou aqui")
		c.JSON(400, gin.H{
			"error": "User already exists",
		})
		return
	}

	if repository.GetUserByEmail(user.Email) != nil {
		c.JSON(400, gin.H{
			"error": "Email already used",
		})
		return
	}

	repository.InsertUser(user)
	c.JSON(200, gin.H{
		"status": "User created successfully!",
	})
}