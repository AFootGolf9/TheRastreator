package entity

type LocationQuery struct {
	InitialTime string `json:"initial_time"`
	FinalTime   string `json:"final_time"`
	Token       string `json:"token"`
	UserId      int    `json:"user_id"`
}
