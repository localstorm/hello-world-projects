
changeOf("ASSETS".resource, "id", "user_id").triggers {
	drop "ASSETS", 
		 id: cause.id
	
	drop "WATCHED_ASSETS", 
		 id: cause.id
	
	causes changeOf("NET_WORTH".resource), 
		   owner_id: cause.user_id
	
	causes changeOf("BALANCE".resource),   
		   asset_id: cause.id
	
	causes changeOf("JUST_FOR_FUN".resource),   
		   id:  value(10)
} 

changeOf("NET_WORTH".resource,  "owner_id").triggers {
	// Currently, doing nothing
}

