        ObjectFactory of = new ObjectFactory();

        JAXBContext jc = JAXBContext.newInstance("ekassirv3protocol");
        GetDirectoryRequest request = of.createGetDirectoryRequest();
        request.setServicesDetalization(BigInteger.ONE);

        JAXBElement<Request> element = of.createRequest(request);
        Marshaller marshaller = jc.createMarshaller();

        StringWriter sw = new StringWriter();
        marshaller.marshal(element, sw);
        System.out.println(sw.toString());

        Unmarshaller unmarshaller = jc.createUnmarshaller();


        JAXBElement<GetDirectoryResponse> response = unmarshaller.unmarshal(new StringSource(sw.toString()), GetDirectoryResponse.class);
        response.getValue();
