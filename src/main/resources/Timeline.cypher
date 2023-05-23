// Nodes
// --- Hijri Timeline
CREATE (Timeline:Timeline)
CREATE (HijriTimeline:HijriTimeline)
CREATE (BeforeHijri:BH)
CREATE (AfterHijri:AH)
CREATE (FiftyThreeYearsBH:Year {value:53})
CREATE (EleventhYearAH:Year {value: 11})
CREATE (TwelfthRabiUlAwwal11AH:Day {day: 'Monday', value: 12})
CREATE (RabiUlAwwal11AH:Month {name:'Rabi-Ul-Awwal'})
CREATE (RaheeqUlMakhtoom:Book {name:'الرحيق المختوم',language:'Ar', author:'صفی الرحمان مبارک پوری', urduTranslation:'صفی الرحمان مبارک پوری', publisher:'ال مكتبة السلفية لاهور', publishedYear:'2021'})

// --- Events
CREATE (BirthOfLastProphet:Event {name:'Birth Of Last Prophet P.B.U.H'})
CREATE (DeathOfLastProphet:Event {name:'Death Of Last Prophet P.B.U.H'})
// Edges
CREATE (Timeline)-[:TIMELINE_TYPE]->(HijriTimeline)
CREATE (HijriTimeline)-[:BEFORE]->(BeforeHijri)
CREATE (HijriTimeline)-[:AFTER]->(AfterHijri)
CREATE (BeforeHijri)-[:YEAR]->(FiftyThreeYearsBH)
CREATE (BirthOfLastProphet)-[:BORN_IN{source:'The History Of Quranic Text From Revelation To Compilation',reference:'Jan 2003 Page 23'}]->(FiftyThreeYearsBH)
CREATE (AfterHijri)-[:YEAR]->(EleventhYearAH)
CREATE (EleventhYearAH)-[:MONTH]->(RabiUlAwwal11AH)
CREATE (RabiUlAwwal11AH)-[:DAY]->(TwelfthRabiUlAwwal11AH)
// --- Source References
CREATE (DeathOfLastProphet)-[:DIED_IN {source:'The History Of Quranic Text From Revelation To Compilation',reference:'Jan 2003 Page 23'}]->(TwelfthRabiUlAwwal11AH)
