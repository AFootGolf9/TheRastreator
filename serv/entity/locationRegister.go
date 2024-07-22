package entity

type LocationRegister struct {
	Position_time string  `json:"position_time"`
	Client_id     int     `json:"client_id"`
	Latitude      float64 `json:"latitude"`
	Longitude     float64 `json:"longitude"`
}

func NewLocationRegister(t string, c int, lat float64, long float64) *LocationRegister {
	return &LocationRegister{
		Position_time: t,
		Client_id:     c,
		Latitude:      lat,
		Longitude:     long,
	}
}
