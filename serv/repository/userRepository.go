package repository

import (
	"AFootGolf9/TheRastreator/entity"
	"AFootGolf9/TheRastreator/util"
)

func InsertUser(user entity.UserJson) {
	_, err := db.Exec("INSERT INTO client (username, email, password) VALUES ($1, $2, $3)",
		user.User, user.Email, util.Encript(user.Pass))
	if err != nil {
		panic(err)
	}
}

func GetUserByName(user string) *entity.UserJson {
	row := db.QueryRow("SELECT * FROM client WHERE username = $1", user)

	var userDB entity.UserJson
	err := row.Scan(&userDB.Id, &userDB.User, &userDB.Email, &userDB.Pass)
	if err != nil {
		if err.Error() == "sql: no rows in result set" {
			return nil
		}
	}

	return &userDB
}

func GetUserByEmail(email string) *entity.UserJson {
	row := db.QueryRow("SELECT * FROM client WHERE email = $1", email)

	var userDB entity.UserJson
	err := row.Scan(&userDB.Id, &userDB.User, &userDB.Email, &userDB.Pass)
	if err != nil {
		if err.Error() == "sql: no rows in result set" {
			return nil
		}
	}

	return &userDB
}

func GetUserIdByName(user string) int {
	row := db.QueryRow("SELECT client_id FROM client WHERE username = $1", user)

	var id int
	err := row.Scan(&id)
	if err.Error() == "sql: no rows in result set" {
		return 0
	}

	return id
}

func GetUsernameById(id int) string {
	row := db.QueryRow("SELECT username FROM client WHERE client_id = $1", id)

	var username string
	err := row.Scan(&username)
	if err != nil {
		if err.Error() == "sql: no rows in result set" {
			return ""
		}
	}
	return username
}
