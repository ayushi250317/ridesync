import axios from "axios"
import { decode } from "@googlemaps/polyline-codec";
import { GMAP_API_KEY } from "./API";

export const getPolyLineCoordinates = (fromAddress, toAddress) => {

    return axios.get(
        `https://maps.googleapis.com/maps/api/directions/json`,
        {
            params: {
                origin: `${fromAddress.lat}, ${fromAddress.lng}`,
                destination: `${toAddress.lat}, ${toAddress.lng}`,
                key: GMAP_API_KEY,
            },
        }
    ).then(response => {
        const data = response.data;
        console.log("dada5", data);
        if (data.status === "OK") {
            const route = data.routes[0];
            console.log({
                polyline: route.overview_polyline.points,
                coordinates: decode(route.overview_polyline.points),
            });
            return decode(route.overview_polyline.points)
        }
    }).catch(err => {
        console.log("Error in getting coordinates:", err);
    });
    // console.log("return obj", retObj);
}
