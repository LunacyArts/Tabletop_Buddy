#pragma once
#include <cpprest/http_listener.h>
#include <cpprest/json.h>

using namespace web;
using namespace web::http;
using namespace web::http::experimental::listener;

#include <iostream>
#include <map>
#include <set>
#include <string>
using namespace std;

#define TRACE(msg)            wcout << msg
#define TRACE_ACTION(a, k, v) wcout << a << L" (" << k << L", " << v << L")\n"
class ServerComms {
	http_listener listener;
	
public:
	ServerComms(const http::uri& url);
	void start_server();
	void stop_server();
	

private:
	void handle_get(http_request request);
	void handle_put(http_request request);
	void handle_post(http_request request);
	void handle_delete(http_request request);
	
	

};

ServerComms::ServerComms(const http::uri& url) : listener(http_listener(url))

{
	listener.support(methods::GET,
		std::tr1::bind(&ServerComms::handle_get,
			this,
			std::tr1::placeholders::_1));
	listener.support(methods::PUT,
		std::tr1::bind(&ServerComms::handle_put,
			this,
			std::tr1::placeholders::_1));
	listener.support(methods::POST,
		std::tr1::bind(&ServerComms::handle_post,
			this,
			std::tr1::placeholders::_1));
	listener.support(methods::DEL,
		std::tr1::bind(&ServerComms::handle_delete,
			this,
			std::tr1::placeholders::_1));
}


int numResponses = 0;



void ServerComms::handle_get(http_request request)
{
	TRACE(L"\nhandle GET : " << numResponses << "\n");
	numResponses++;

	request.reply(status_codes::OK, "value returned GET");
}


void ServerComms::handle_post(http_request request)
{
	TRACE("\nhandle POST\n");


	request.reply(status_codes::OK, "value returned POST");

}

void ServerComms::handle_put(http_request request)
{
	TRACE("\nhandle PUT\n");
	request.reply(status_codes::OK, "value returned PUT");
}

void ServerComms::handle_delete(http_request request)
{
	TRACE("\nhandle DEL\n");
	request.reply(status_codes::OK, "value returned DEL");
}


void ServerComms::start_server(){
	
	try
	{
		listener
			.open()
			.then([]() {TRACE(L"\nstarting to listen\n"); })
			.wait();
			

		
	}
	catch (exception const & e)
	{
		wcout << e.what() << endl;

	}
}

void ServerComms::stop_server() {
	listener.close();

}