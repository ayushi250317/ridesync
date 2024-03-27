import React, { useState, useEffect, useRef } from 'react';
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
    Spinner,
    Center
} from '@chakra-ui/react';
import axios from 'axios';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { API } from '../API';
import { dateAndTimeInString } from '../Utils';

const ChatDrawer = ({ isOpen, onClose, chatPartnerId, chatPartnerName }) => {
    const [messages, setMessages] = useState([]);
    const [inputValue, setInputValue] = useState('');
    const [chatIdentifier, setChatIdentifier] = useState(null);
    const [loggedInUserDetails, setLoggedInUserDetails] = useState({});
    const isMobile = useBreakpointValue({ base: true, md: false });
    const [isLoading, setIsLoading] = useState(null);
    const stompClient = useRef(null);

    useEffect(() => {
        setIsLoading(true);
        const loggedInUserInfo = JSON.parse(localStorage.getItem('loggedInUserDetails'));
        setLoggedInUserDetails(loggedInUserInfo);
        const asyncFunction = async () => {
            try {
                if (isOpen) {
                    await connect(loggedInUserInfo);
                    const config = {
                        headers: { Authorization: `Bearer ${loggedInUserInfo.token}` }
                    };
                    const chatIdentifierResponse = await axios.get(`${API}/message/chatIdentifier/${chatPartnerId}`, config);
                    if (chatIdentifierResponse.data.success) {
                        setChatIdentifier(chatIdentifierResponse.data.responseObject);
                        const messageHistoryResponse = await axios.get(`${API}/message/messageHistory/${chatPartnerId}`, config);
                        setMessages(messageHistoryResponse.data.responseObject);
                        subscribe(chatIdentifierResponse.data.responseObject);
                    }
                }
            } catch (error) {
                console.error('There was an error:', error);
            } finally {
                setIsLoading(false);
            }
        };

        asyncFunction();
    }, [isOpen, chatPartnerId]);

    const connect = () => {
        return new Promise((resolve, reject) => {
            var chatUrl = API.replace('/api/v1', '');
            const socket = new SockJS(`${chatUrl}/chat`);
            stompClient.current = Stomp.over(socket);
            stompClient.current.connect({}, (frame) => {
                resolve(frame);
            }, (error) => {
                console.error('Connection error: ' + error);
                reject(error);
            });
        });
    };
    const disconnect = () => {
        if (stompClient.current !== null) {
            stompClient.current.disconnect();
        }
    };

    const subscribe = (chatIdentifier) => {
        var chatUrl = API.replace('/api/v1', '');
        stompClient.current.subscribe(`/queue/messages/${chatIdentifier}`, (message) => {
            const jsonString = Object.keys(message._binaryBody).map(key => String.fromCharCode(message._binaryBody[key])).join('');
            const jsonObject = JSON.parse(jsonString);
            setMessages((prevMessage) => [...prevMessage, jsonObject]);
        });
    }

    const sendMessage = () => {
        if (inputValue.trim() !== '') {
            const message = { sentTime: new Date().toISOString(), message: inputValue, senderId: loggedInUserDetails.user.userId, recipientId: chatPartnerId, chatIdentifier };
            stompClient.current.send(`/app/send/${chatIdentifier}`, {}, JSON.stringify(message));
            setInputValue('');
        }
    };

    return (
        <Drawer isOpen={isOpen}
            placement="right"
            onClose={() => { disconnect(); onClose(); }}
            size={isMobile ? "full" : 'md'}>
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Chat with{' ' + chatPartnerName}</DrawerHeader>
                {isLoading ? <Center h="80vh">
                    <Spinner size='xl' />
                </Center> : <>
                    <DrawerBody>
                        <VStack align="stretch" spacing={4} overflowY="auto">
                            {messages.map((message) => (
                                <VStack key={message.id} align={message.senderId !== chatPartnerId ? 'flex-end' : 'flex-start'} spacing={0}>
                                    <HStack key={message.id} justifyContent={message.senderId !== chatPartnerId ? 'flex-end' : 'flex-start'}>
                                        <Text p={2} bg={message.senderId !== chatPartnerId ? 'blue.200' : 'green.200'} borderRadius="lg">
                                            {message.message}
                                        </Text>
                                    </HStack>
                                    <Text fontSize="sm" color="gray.500">{dateAndTimeInString(message.sentTime)}</Text>
                                </VStack>
                            ))}
                        </VStack>
                    </DrawerBody>

                    <DrawerFooter borderTopWidth="1px" mb="4">
                        <Input
                            placeholder="Type a message..."
                            value={inputValue}
                            onChange={(e) => setInputValue(e.target.value)}
                            onKeyPress={(e) => e.key === 'Enter' && sendMessage()}
                        />
                        <Button ml={2} onClick={sendMessage} backgroundColor="#383838" color="white">Send</Button>
                    </DrawerFooter>
                </>

                }
            </DrawerContent>
        </Drawer >
    );
};

export default ChatDrawer;
