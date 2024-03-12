import { Box, Flex, Text } from '@chakra-ui/react'
import React from 'react'
import { IoChatboxEllipsesSharp } from 'react-icons/io5'

const NotificationCard = () => {
    return (
        <Flex alignItems="center" justifyContent="space-between" borderRadius="xl" border="1px solid lightgray" m="3" p="5" >
            <Flex w="90%">

                <Box borderRadius="20px" border="1px solid blue" height="15px" width="15px" backgroundColor="blue" mt="2">

                </Box>
                <Box ml="3" w="90%" >
                    <Text fontSize="large" >
                        Meer Patel has messaged you while driving.
                    </Text>
                    <Text color="gray"> Feb 23, 2024 at 09:15 AM</Text>
                </Box>
            </Flex>
            {/* <Box mt="5"> */}

            <IoChatboxEllipsesSharp size="33px" />
            {/* </Box> */}
        </Flex>
    )
}

export default NotificationCard