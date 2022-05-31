-- SELECT * FROM searchVideos('video');
DROP FUNCTION IF EXISTS searchVideos;
CREATE FUNCTION searchVideos(search_key varchar)

RETURNS TABLE(
	fileid bigint,
	created_at timestamp without time zone,
	name varchar,
	size int
)

AS $$
BEGIN
RETURN QUERY
 WITH v AS (SELECT * FROM videos WHERE deleted is false)

 SELECT v.fileid,v.created_at,v.name,v.size
     FROM v WHERE v.name ILIKE concat('%',search_key,'%') OR cast(v.size as varchar) = search_key;
END;
$$
LANGUAGE plpgsql;
