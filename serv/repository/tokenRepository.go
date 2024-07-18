package repository

import "AFootGolf9/TheRastreator/util"

func NewToken(client int) string {
	token := util.RandomToken()
	_, err := db.Exec("INSERT INTO token (token, client_id) VALUES ($1, $2)", token, client)
	if err != nil {
		panic(err)
	}
	return token
}

func GetClientByToken(token string) int {
	row := db.QueryRow("SELECT client_id FROM token WHERE token = $1", token)

	var client int
	err := row.Scan(&client)
	if err != nil {
		panic(err)
	}

	_, err = db.Exec("UPDATE token SET time = NOW() WHERE client_id = $1", token)
	if err != nil {
		panic(err)
	}

	return client
}
