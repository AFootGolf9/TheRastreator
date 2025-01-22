package entity

import "time"

type LocationQuery struct {
	InitialTime time.Time `json:"initial_time"`
	FinalTime   time.Time `json:"final_time"`
	Token       string    `json:"token"`
	UserId      int       `json:"user_id"`
}
