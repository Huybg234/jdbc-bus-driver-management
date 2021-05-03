create table driver(
	id number primary key,
	name nvarchar2(30) not null,
	address nvarchar2(50) not null,
	phone_number nvarchar2(10) not null,
	driver_level nvarchar2(2) not null
);


create table bus_line(
	id number primary key,
	distance number not null,
	bus_stop_number number not null
);


create table driving_assignment(
	driver_id number not null,
	bus_line_id number not null,
	round_trip_number number not null,
	constraint driving_assignment_PK primary key (driver_id, bus_line_id)
);



select d.id driver_id, d.name, d.address, d.phone_number, d.driver_level, bl.id bus_line_id, d.distance, d.bus_stop_number, da.round_trip_number
from driving_assignment da join driver d on da.driver_Id = d.id join bus_line bl on da.bus_line_id = bl.id