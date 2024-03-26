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

const ChatDrawer = ({ isOpen, onClose, chatPartnerId }) => {
    console.log(chatPartnerId)
    const [messages, setMessages] = useState([]);
    const [inputValue, setInputValue] = useState('');
    const [chatIdentifier, setChatIdentifier] = useState(null);
    const [connected, setConnected] = useState(false);
    const [loggedInUserDetails, setLoggedInUserDetails] = useState({});
    const isMobile = useBreakpointValue({ base: true, md: false });
    const [isLoading, setIsLoading] = useState(null);
    const stompClient = useRef(null);

    useEffect(() => {
        setIsLoading(true);
        const loggedInUserInfo = JSON.parse(localStorage.getItem('loggedInUserDetails'));
        setLoggedInUserDetails(loggedInUserInfo);
        console.log(loggedInUserInfo)
        const asyncFunction = async () => {
            try {
                if (isOpen) {
                    await connect(loggedInUserInfo);
                    const config = {
                        headers: { Authorization: `Bearer ${loggedInUserInfo.token}` }
                    };
                    const chatIdentifierResponse = await axios.get(`${API}/message/chatIdentifier/${chatPartnerId}`, config);
                    if (chatIdentifierResponse.data.success) {
                        console.log(chatIdentifierResponse);
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

    const connect = (loggedInUserInfo) => {
        return new Promise((resolve, reject) => {
            const socket = new SockJS(`${API}/chat`, {

                headers: {
                    "Authorization": `Bearer ${loggedInUserInfo.token}`
                }
            });
            stompClient.current = Stomp.over(socket);
            stompClient.current.connect({}, (frame) => {
                setConnected(true);
                console.log('Connected: ' + frame);
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
        setConnected(false);
        console.log("Disconnected");
    };

    const subscribe = (chatIdentifier) => {
        stompClient.subscribe(`/topic/${chatIdentifier}`, (message) => {
            setMessages((prevMessage) => [...prevMessage, message.body.message]);
        });
    }

    const sendMessage = () => {
        if (inputValue.trim() !== '') {
            const message = { id: Date.now(), text: inputValue, sender: 'user' };
            stompClient.send("/app/message", {}, JSON.stringify(message));
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
                <DrawerHeader>Chat</DrawerHeader>
                {isLoading ? <Center h="80vh">
                    <Spinner size='xl' />
                </Center> : <>
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
                </>

                }
            </DrawerContent>
        </Drawer >
    );
};

export default ChatDrawer;
