package entity

import "AFootGolf9/TheRastreator/util"

type UserJson struct {
	User  string `json:"user"`
	Email string `json:"email"`
	Pass  string `json:"pass"`
}

func NewUserJson(user string, email string, pass string) *UserJson {
	return &UserJson{
		User:  user,
		Email: email,
		Pass:  pass,
	}
}

func (l *UserJson) ValidatePass(pass string) bool {
	return l.Pass == util.Encript(pass)
}
