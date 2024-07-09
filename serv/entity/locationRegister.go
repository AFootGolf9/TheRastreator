package entity

type LocationRegister struct {
	Position_time string  `json:"position_time"`
	Latitude      float64 `json:"latitude"`
	Longitude     float64 `json:"longitude"`
}

func NewLocationRegister(t string, lat float64, long float64) *LocationRegister {
	return &LocationRegister{
		Position_time: t,
		Latitude:      lat,
		Longitude:     long,
	}
}
