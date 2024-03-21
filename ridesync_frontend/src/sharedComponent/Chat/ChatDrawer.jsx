import React, { useState, useEffect } from 'react';
import {
    Button,
    Drawer,
    DrawerBody,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    DrawerContent,
    DrawerCloseButton,
    VStack,
    HStack,
    Text,
    Input,
    useBreakpointValue,
} from '@chakra-ui/react';
import axios from 'axios';
import { API } from '../API';

const ChatDrawer = ({ isOpen, onClose, chatPartnerId }) => {
    const [messages, setMessages] = useState([]);
    const [inputValue, setInputValue] = useState('');
    const [chatIdentifier, setChatIdentifier] = useState(null);
    const [loggedInUserDetails, setLoggedInUserDetails] = useState({});
    const isMobile = useBreakpointValue({ base: true, md: false });
    const [isLoading, setIsLoading] = useState(null);

    useEffect(() => {
        const loggedInUserInfo = JSON.parse(localStorage.getItem('loggedInUserDetails'));
        setLoggedInUserDetails(loggedInUserInfo);

    }, [])
    useEffect(() => {
        if (isOpen) {
            setIsLoading(true)
            const config = {
                headers: { Authorization: `Bearer ${loggedInUserDetails.token}` }
            };
            axios.get(`${API}/ride/getRideDetail/`, config)
                .then(response => {
                    setMessages(response.data.messages);
                })
                .catch(error => console.error('There was an error loading the messages:', error))
                .finally(() => setIsLoading(false));
        }
    }, [isOpen, chatPartnerId]);

    const sendMessage = () => {
        if (inputValue.trim() !== '') {
            const message = { id: Date.now(), text: inputValue, sender: 'user' };
            // Here, implement the API call to send the message, for example:
            //   axios.post('https://yourapi/messages/send', { ...message, chatPartnerId })
            //     .then(response => {
            //       // Handle response, e.g., adding the message to the chat
            //       setMessages(prev => [...prev, message]);
            //     })
            //     .catch(error => console.error('There was an error sending the message:', error));
            setInputValue('');
        }
    };

    return (
        <Drawer isOpen={isOpen} placement="right" onClose={onClose} size={isMobile ? "full" : 'md'}>
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Chat</DrawerHeader>

                <DrawerBody>
                    <VStack align="stretch" spacing={4} overflowY="auto">
                        {messages.map((message) => (
                            <HStack key={message.id} justifyContent={message.sender === 'user' ? 'flex-end' : 'flex-start'}>
                                <Text p={2} bg={message.sender === 'user' ? 'blue.200' : 'green.200'} borderRadius="lg">
                                    {message.text}
                                </Text>
                            </HStack>
                        ))}
                    </VStack>
                </DrawerBody>

                <DrawerFooter borderTopWidth="1px">
                    <Input
                        placeholder="Type a message..."
                        value={inputValue}
                        onChange={(e) => setInputValue(e.target.value)}
                        onKeyPress={(e) => e.key === 'Enter' && sendMessage()}
                    />
                    <Button ml={2} onClick={sendMessage}>Send</Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    );
};

export default ChatDrawer;
