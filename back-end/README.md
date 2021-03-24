# Tables Till Now
Tables DB (as for now)

Users
|Column|Type|Description|Properties|
|-|-|-|-|
|Userid|int|id|PK|
|isAdmin|boolean|admin Flag||
|username|VARCHAR|Username value|Unique|
|Password|VARCHAR| Hashed Password||
|isProvider|boolean|provider Flag||

Vehicle
|Column|Type|Description|Properties|
|-|-|-|-|
|VehicleID|int|id|PK|
|UserID|int| owner's ID| Ref. Users.Userid|
|VehicleType|VARCHAR|Type Of Vehicle||

EnergyProvider
|Column|Type|Description|Properties|
|-|-|-|-|
|ProviderID|int|id|PK|
|ProviderName|VARCHAR|Provider's name||



Station
|Column|Type|Description|Properties|
|-|-|-|-|
|StationID|int|id|PK|
|StationOperator|Varchar| Name of operator||
|BusyFactor|int (0 to 5)| Used as Business Metric||
|EnergyProviderID|int| EnergyProvider's Identification|Ref. EnergyProvider.ProviderID|
|latitude|VARCHAR|Station's Latitude||
|longitude|VARCHAR|Station's Longitude||

ChargerType
|Column|Type|Description|Properties|
|-|-|-|-|
|ChargerID|int|id|PK|
|ChargerName|VARCHAR| charger's name||
|ChargerOutput|float| charger's output in KWh||

Point
|Column|Type|Description|Properties|
|-|-|-|-|
|PointID|int|id|PK|
|Point_Operator|VARCHAR|Name of Point's operator||
|StationID|int|Id of station Point belongs to|Ref Station.StationID|
|CostPerKwh|float|cost per Kwh provided||
|PricingPolicy|VARCHAR|PricingPolicy chosen||
|ChargerType|List<ChargerType>| List of Charger Types Available|Ref. ChargerType|

Session
|Column|Type|Description|Properties|
|-|-|-|-|
|SessionID|int|id|PK|
|ConnectionTime|Timestamp|Time EV Connected to charger||
|DisconnectTime|Timestamp|Time  EV disconnected from charger||
|CostPerKwh|Float|cost per kwh applied to session||
|EnergyDelivered|float|total energy delivered to EV||
|PricePolicyRef|VARCHAR|Price Policy||
|PointID|int|Point Charging took place|Ref. Point.PointID|
|VehicleID|int|Vehicle Charged| Ref. Vehicle.VehicleID|
|Payment|VARCHAR|Way of Payment||
|Protocol|VARCHAR|Charging Protocol||

# Back-end

Ενδεικτικά περιεχόμενα:

- Πηγαίος κώδικας εφαρμογής για εισαγωγή, διαχείριση και
  πρόσβαση σε δεδομένα (backend).
- Database dump (sql ή json)
- Back-end functional tests.
- Back-end unit tests.
- RESTful API.
