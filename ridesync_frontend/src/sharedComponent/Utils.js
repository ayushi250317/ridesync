export const dateAndTimeInString = (dateTimeArray) => {
    if (dateTimeArray && dateTimeArray.length > 0) {
        const [year, month, day, hour, minute] = dateTimeArray;
        const date = new Date(Date.UTC(year, month - 1, day, hour, minute));
        const dateOptions = {
            weekday: 'long',
            month: 'long',
            day: 'numeric',
            timeZone: 'America/Halifax',
        };
        const timeOptions = {
            hour: 'numeric',
            minute: 'numeric',
            hour12: true,
            timeZone: 'America/Halifax',
        };

        const formattedDate = date.toLocaleDateString('en-US', dateOptions);
        const formattedTime = date.toLocaleTimeString('en-US', timeOptions);
        const result = `${formattedDate} at ${formattedTime.toLowerCase()}`;
        return result;
    } else {
        return "";
    }
}

export const extractAddress = (address) => {
    return address && address.split(', ')[0] ? address.split(', ')[0] : address
}