import React, { useState, useCallback, useEffect } from 'react';
import { Box, Input, Button, Modal, ModalOverlay, ModalContent, ModalHeader, ModalCloseButton, ModalBody, useDisclosure, Flex, InputRightElement, InputGroup, Text } from '@chakra-ui/react';
import { GoogleMap, MarkerF, useJsApiLoader, Autocomplete } from '@react-google-maps/api';
import { IoLocation } from 'react-icons/io5';
import { ImLocation2 } from 'react-icons/im';
import { FaRegCircle } from 'react-icons/fa';

const libraries = ['places'];

const MapSelectorModal = ({ isOpen, onClose, onLocationSelect, locationInfo }) => {
  const [marker, setMarker] = useState(null);
  useEffect(() => {
    if (isOpen && locationInfo.lat !== null && locationInfo.lng !== null) {
      setMarker({ lat: locationInfo.lat, lng: locationInfo.lng });
    }
  }, [isOpen]);

  const onMapClick = useCallback((event) => {
    setMarker({ lat: event.latLng.lat(), lng: event.latLng.lng() });
  }, []);

  const onDone = useCallback(() => {
    if (!marker) return;

    const geocoder = new window.google.maps.Geocoder();
    geocoder.geocode({ location: marker }, (results, status) => {
      console.log(results)
      if (status === 'OK' && results[0]) {
        onLocationSelect({ ...marker, address: results[0].formatted_address });
        onClose();
      }
    });
  }, [marker, onLocationSelect, onClose]);

  return (
    <Modal isOpen={isOpen} onClose={onClose} size="xl" isCentered>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Select a Location</ModalHeader>
        <ModalCloseButton />
        <ModalBody>
          <Box height="400px" width="100%">
            <GoogleMap
              key={isOpen ? "map-open" : "map-closed"} // Change key based on modal state
              mapContainerStyle={{ width: '100%', height: '100%' }}
              center={marker || { lat: 44.6488, lng: -63.5752 }}
              zoom={15}
              onClick={onMapClick}
            >
              {marker && <MarkerF position={marker} />}
            </GoogleMap>
          </Box>
          <Button colorScheme="blue" onClick={onDone} mt={4}>Done</Button>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

const AddressInput = ({ label, setAddressInfo, addressInfo }) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [autocomplete, setAutoComplete] = useState(null);
  const onLoad = (autocomplete) => {
    setAutoComplete(autocomplete);
  }
  const handlePlaceChanged = () => {
    const place = autocomplete?.getPlace();
    console.log("asdsa", autocomplete);
    if (place?.formatted_address) {
      var lat = place.geometry.location.lat();
      var lng = place.geometry.location.lng();
      setAddressInfo({ address: place.formatted_address, lat, lng });
    }
  };

  return (
    <Flex w={"100%"} marginBottom={["10px", "10px", "15px", "15px"]} >
      <Autocomplete onLoad={onLoad} onPlaceChanged={handlePlaceChanged}>
        <InputGroup size='md' w="100%">
          <Input
            placeholder={label}
            value={addressInfo.address}
            onChange={(e) => { setAddressInfo((prevState) => { return { ...prevState, address: e.target.value } }) }}
            w={["81vw", "87vw", "60vw", "64.7vw"]}
          />
          <InputRightElement >
            <Button size='large' boxShadow="xl" backgroundColor="transparent" onClick={onOpen}>
              <IoLocation />
            </Button>
          </InputRightElement>
        </InputGroup>
      </Autocomplete>
      <MapSelectorModal isOpen={isOpen} onClose={onClose} onLocationSelect={setAddressInfo} locationInfo={addressInfo} />
    </Flex>
  );
};

const Locations = ({ fromAddress, toAddress, setFromAddress, setToAddress }) => {
  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: "AIzaSyAgF12Bol5WhhLKL8WFlTc-qjMlPjugM3A",
    libraries,
  });

  if (!isLoaded) return <div>Loading...</div>;

  return (
    <Box>
      <Flex justifyContent="space-evenly" alignItems="center">
        <Box>
          <FaRegCircle size="20px" color='blue' className='mx-2 mb-3' />
        </Box>
        <AddressInput label="From" setAddressInfo={setFromAddress} addressInfo={fromAddress} />
      </Flex>
      <Flex justifyContent="space-evenly" alignItems="center">
        <Box>
          <ImLocation2 size="28px" color='green' className='mx-1 mb-3' />
        </Box>
        <AddressInput label="To" setAddressInfo={setToAddress} addressInfo={toAddress} />
      </Flex>
    </Box>
  );
};

export default Locations;
