import { Box, Flex, Text } from '@chakra-ui/react'
import React from 'react'
import { IoChatboxEllipsesSharp } from 'react-icons/io5'
import { FaCar } from 'react-icons/fa'
import { notificationEnum } from '../../sharedComponent/Enums/NotificationEnums'

const NotificationCard = ({ cardAttributes }) => {

    const getMonthName = (index) => {
        const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        return months[index - 1]
    }

    const formatTime = (hours, minutes) => {
        let hour = hours % 12 || 12;
        let period = hours < 12 ? 'AM' : 'PM';
        let paddedMinutes = minutes < 10 ? '0' + minutes : minutes;
        let formattedTime = hour + ':' + paddedMinutes + ' ' + period;
        return formattedTime;
    }

    return (
        <Flex alignItems="center" justifyContent="space-between" borderRadius="xl" border="1px solid lightgray" m="3" p="5" >
            <Flex w="90%">

                <Box borderRadius="20px" border="1px solid blue" height="15px" width="15px" backgroundColor="blue" mt="2">

                </Box>
                <Box ml="3" w="90%" >
                    <Text fontSize="large" >
                        {cardAttributes.message}.
                        {/* Meer Patel has messaged you while driving. */}
                    </Text>
                    <Text color="gray"> {getMonthName(cardAttributes.timeStamp[1])} {cardAttributes.timeStamp[2]}, {cardAttributes.timeStamp[0]} at {formatTime(cardAttributes.timeStamp[3], cardAttributes.timeStamp[4])}</Text>
                </Box>
            </Flex>
            {cardAttributes.notificationType === notificationEnum.CHAT ?
                <IoChatboxEllipsesSharp size="33px" /> : <FaCar size="30px" />
            }
        </Flex>
    )
}

export default NotificationCard