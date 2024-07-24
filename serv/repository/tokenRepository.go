package repository

import (
	"AFootGolf9/TheRastreator/util"
	"fmt"
	"time"
)

func NewToken(client int) string {
	fmt.Print(client)
	token := util.RandomToken()
	_, err := db.Exec("INSERT INTO token (token, client_id) VALUES ($1, $2)", token, client)
	if err != nil {
		panic(err)
	}
	return token
}

func GetClientByToken(token string) int {
	row := db.QueryRow("SELECT client_id, time FROM token WHERE token = $1", token)

	var t time.Time
	var client int
	err := row.Scan(&client, &t)
	if err != nil {
		if err.Error() == "sql: no rows in result set" {
			return 0
		}
	}

	if t.Add(time.Hour * 24).Before(time.Now()) {
		deleteToken(token)
		return 0
	}

	_, err = db.Exec("UPDATE token SET time = NOW() WHERE client_id = $1", client)
	if err != nil {
		panic(err)
	}

	return client
}

func deleteToken(token string) {
	_, err := db.Exec("DELETE FROM token WHERE token = $1", token)
	if err != nil {
		panic(err)
	}
}
