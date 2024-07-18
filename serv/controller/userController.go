package controller

import (
	"AFootGolf9/TheRastreator/entity"
	"AFootGolf9/TheRastreator/repository"
)

func InsertNewUser(User entity.UserJson) {
	repository.InsertNewUser(User)
}

func GetUserByName(user string) *entity.UserJson {
	return repository.GetUserByName(user)
}

func GetUserByEmail(email string) *entity.UserJson {
	return repository.GetUserByEmail(email)
}

func ValidateUser(user string, pass string) bool {
	userDB := GetUserByName(user)
	if userDB == nil {
		return false
	}
	return userDB.ValidatePass(pass)
}
