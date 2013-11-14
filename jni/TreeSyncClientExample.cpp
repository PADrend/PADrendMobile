//#include <Util/Network/DataConnection.h>
//#include <Util/Network/Network.h>
//#include <Util/Network/NetworkTCP.h>
//#include <MinSG/Ext/TreeSync/Server.h>
//
//	const auto serverAddress = Util::Network::IPAddress::resolveHost("myserver", 12345);
//	auto tcpConnection = Util::Network::TCPConnection::connect(serverAddress);
//	if(tcpConnection.isNull()) {
//		return;
//	}
//	Util::Reference<Util::Network::DataConnection> dataConnection = new Util::Network::DataConnection(tcpConnection.get());
//	if(dataConnection.isNull()) {
//		return;
//	}
//	MinSG::TreeSync::TreeSyncClient treeSyncClient(dataConnection.get());
//
//	// Do the following regularly (e.g. in each frame)
//	treeSyncClient.execute(*sceneMgr);
